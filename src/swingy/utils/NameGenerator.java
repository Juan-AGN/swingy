package swingy.utils;

public final class NameGenerator {
    public static String generateName() {
        int name = TrueRandom.getrandom(2) + 1;
        int lastName = TrueRandom.getrandom(2) + 1;
        int vowel = 0;
        int other = 0;
        String[] vowels = {"a", "e", "i", "o", "u"};
        String[] others = {"b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "ñ", "p", "q", "r", "s", "t", "v", "w", "x", "t", "z"};
    
        String toRet = "";

        while (name >= 0)
        {
            if (vowel != 2 && (other == 2 || TrueRandom.getrandom(2) == 1))
            {
                if (other == 0 && vowel == 0)
                    toRet += vowels[TrueRandom.getrandom(vowels.length)].toUpperCase();
                else
                    toRet += vowels[TrueRandom.getrandom(vowels.length)];
                vowel ++;
                other = 0;
                if (name != 0)
                    name --;
                else if (TrueRandom.getrandom(2) > 0)
                    name --;
            }
            else
            {
                if (other == 0 && vowel == 0)
                    toRet += others[TrueRandom.getrandom(others.length)].toUpperCase();
                else
                    toRet += others[TrueRandom.getrandom(others.length)];
                if (name == 0 && vowel == 2)
                    break;
                other ++;
                vowel = 0;
            }
        }

        toRet += " ";
        other = 0;
        vowel = 0;

        while (lastName >= 0) {
            if (vowel != 2 && (other == 2 || TrueRandom.getrandom(2) == 1))
            {
                if (other == 0 && vowel == 0)
                    toRet += vowels[TrueRandom.getrandom(vowels.length)].toUpperCase();
                else
                    toRet += vowels[TrueRandom.getrandom(vowels.length)];
                vowel ++;
                other = 0;
                if (lastName != 0)
                    lastName --;
                else if (TrueRandom.getrandom(2) > 0)
                    lastName --;
            }
            else
            {
                if (other == 0 && vowel == 0)
                    toRet += others[TrueRandom.getrandom(others.length)].toUpperCase();
                else
                    toRet += others[TrueRandom.getrandom(others.length)];
                if (lastName == 0 && vowel == 2)
                    break;
                other ++;
                vowel = 0;
            }
        }
        return (toRet);
    }
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i ++)
            System.out.println(NameGenerator.generateName());
    }
}
