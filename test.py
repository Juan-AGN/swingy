#!/usr/bin/env python3
"""Per-level stats for the ASCII map dump.

Usage: python analyze_maps.py maps.txt [--csv out.csv]

Reports: size, pool, open-tile %, number of disconnected regions (4-neighbour),
size of the largest region, open tiles outside it, and border openings.
"""
import sys
import csv
from collections import deque

OPEN = "░"


def parse(path):
    levels = []
    with open(path, encoding="utf-8") as f:
        lines = [l.rstrip("\n") for l in f]
    i = 0
    while i < len(lines):
        if lines[i].strip().startswith("LEVEL:"):
            level = int(lines[i + 1].strip())
            pool = lines[i + 3].strip()
            i += 4
            assert lines[i].startswith("o-"), f"level {level}: missing top border"
            i += 1
            rows = []
            while not lines[i].startswith("o-"):
                rows.append(lines[i].strip()[1:-1])  # strip the | borders
                i += 1
            i += 1
            levels.append((level, pool, rows))
        else:
            i += 1
    return levels


def regions(grid):
    h, w = len(grid), len(grid[0])
    seen = [[False] * w for _ in range(h)]
    sizes = []
    for y in range(h):
        for x in range(w):
            if grid[y][x] == OPEN and not seen[y][x]:
                q = deque([(y, x)])
                seen[y][x] = True
                n = 0
                while q:
                    cy, cx = q.popleft()
                    n += 1
                    for dy, dx in ((1, 0), (-1, 0), (0, 1), (0, -1)):
                        ny, nx = cy + dy, cx + dx
                        if 0 <= ny < h and 0 <= nx < w and not seen[ny][nx] and grid[ny][nx] == OPEN:
                            seen[ny][nx] = True
                            q.append((ny, nx))
                sizes.append(n)
    return sorted(sizes, reverse=True)


def border_openings(grid):
    h, w = len(grid), len(grid[0])
    count = 0
    for x in range(w):
        count += grid[0][x] == OPEN
        count += grid[h - 1][x] == OPEN
    for y in range(1, h - 1):
        count += grid[y][0] == OPEN
        count += grid[y][w - 1] == OPEN
    return count


def main():
    path = sys.argv[1]
    out = sys.argv[sys.argv.index("--csv") + 1] if "--csv" in sys.argv else None
    rows_out = []
    print(f"{'lvl':>3} {'size':>9} {'pool':<24} {'open%':>6} {'regions':>7} {'largest':>8} {'orphan':>7} {'border':>6}")
    for level, pool, grid in parse(path):
        h, w = len(grid), len(grid[0])
        total_open = sum(r.count(OPEN) for r in grid)
        sizes = regions(grid)
        largest = sizes[0] if sizes else 0
        orphan = total_open - largest
        border = border_openings(grid)
        pct = 100 * total_open / (h * w)
        print(f"{level:>3} {w:>4}x{h:<4} {pool:<24} {pct:>5.1f}% {len(sizes):>7} {largest:>8} {orphan:>7} {border:>6}")
        rows_out.append([level, w, h, pool, total_open, round(pct, 2), len(sizes), largest, orphan, border])
    if out:
        with open(out, "w", newline="") as f:
            wr = csv.writer(f)
            wr.writerow(["level", "w", "h", "pool", "open", "open_pct", "regions", "largest", "orphan", "border"])
            wr.writerows(rows_out)


if __name__ == "__main__":
    main()