package com.example.gogoooma.graduationproject;

import java.util.HashMap;
import java.util.Map;

public class Emoji {

    static HashMap<String, String> xlat = new HashMap<String, String>();

    public Emoji(){
        EmojiHashMap();
    }

    public static String checkEmoji(String string){
        String real_message = "";
        String stringbuilder = "";
        for(int i=0; i<string.length();i++) {
            try {
                stringbuilder = new StringBuilder().appendCodePoint(string.codePointAt(string.offsetByCodePoints(0,i))).toString();
                //System.out.println("stringbuilder : " + stringbuilder);
                for(String key : xlat.keySet()) {
                    if(stringbuilder.equals(key)) {
                        //System.out.println(xlat.get(key));
                        stringbuilder = xlat.get(key);
                    }
                }
            }catch(StringIndexOutOfBoundsException e) {
                break;
            }
            real_message += stringbuilder;
            //System.out.println();
        }
        //System.out.println(real_message);
        return real_message;
    }
    public static void EmojiHashMap(){
        xlat.put("😀","웃음");
        xlat.put("😃","눈 크게 뜨고 웃음");
        xlat.put("😄","눈 웃음치며 웃음");
        xlat.put("😁","눈 웃음치며 밝게 웃음");
        xlat.put("😆","><");
        xlat.put("😅","살짝 당황");
        xlat.put("🤣","너무 웃기다");
        xlat.put("😂","기쁨의 눈물");
        xlat.put("🙂","미소");
        xlat.put("😉","윙크");
        xlat.put("😊","눈 웃음치며 미소");
        xlat.put("😇","천사");
        xlat.put("😍","하트 하트");
        xlat.put("🤩","황홀");
        xlat.put("😘","사랑스럽게 뽀뽀");
        xlat.put("😗","뽀뽀");
        xlat.put("☺","웃음");
        xlat.put("😚","웃으며 뽀뽀");
        xlat.put("😙","눈 웃음  뽀뽀");
        xlat.put("😋","맛있겠다");
        xlat.put("😛","메롱");
        xlat.put("😜","윙크 메롱");
        xlat.put("😝","약올리기");
        xlat.put("🤫","조용");
        xlat.put("🤔","추측중");
        xlat.put("🤨","못 미더움");
        xlat.put("😐","평범");
        xlat.put("😑","뚱");
        xlat.put("😏","으쓱");
        xlat.put("😒","삐짐");
        xlat.put("🤥","거짓말");
        xlat.put("😌","평범");
        xlat.put("😴","졸리다");
        xlat.put("😷","감기걸림");
        xlat.put("🤮","토하다");
        xlat.put("😵","어지러움");
        xlat.put("😎","잘난척");
        xlat.put("😕","헷갈림");
        xlat.put("😟","걱정");
        xlat.put("🙁","조금 못마땅");
        xlat.put("☹","못마땅");
        xlat.put("😲","놀람");
        xlat.put("😰","걱정");
        xlat.put("😥","슬픔");
        xlat.put("😢","울음");
        xlat.put("😭","펑펑 울음");
        xlat.put("😱","공포");
        xlat.put("😫","피곤");
        xlat.put("😤","화남");
        xlat.put("😡","매우 화남");
        xlat.put("😠","화남");
        xlat.put("😈","악마");
        xlat.put("💀","해골");
        xlat.put("☠","해골");
        xlat.put("💩","똥");
        xlat.put("💋","뽀뽀");
        xlat.put("💌","사랑");
        xlat.put("💘","사랑");
        xlat.put("💝","사랑");
        xlat.put("💖","사랑");
        xlat.put("💗","사랑");
        xlat.put("💓","사랑");
        xlat.put("💞","사랑");
        xlat.put("💕","사랑");
        xlat.put("💟","사랑");
        xlat.put("❣","사랑");
        xlat.put("💔","정 떨어짐");
        xlat.put("❤","사랑");
        xlat.put("🧡","사랑");
        xlat.put("💛","사랑");
        xlat.put("💚","사랑");
        xlat.put("💙","사랑");
        xlat.put("💜","사랑");
        xlat.put("🖤","사랑");
        xlat.put("💥","폭발");
        xlat.put("👌","확인");
        xlat.put("✌","자랑");
        xlat.put("👍","잘했다");
        xlat.put("👊","잘했다");
        xlat.put("🤛","잘했다");
        xlat.put("🤜","잘했다");
        xlat.put("👏","잘했다");
        xlat.put("🙌","만세");
        xlat.put("💏","사랑");
        xlat.put("👩 ❤️ 💋 👨","사랑");
        xlat.put("👨 ❤️ 💋 👨","사랑");
        xlat.put("👩 ❤️ 💋 👩","사랑");
        xlat.put("💑","사랑");

        xlat.put("\uD83E\uDD17","환한 미소");
        xlat.put("\uD83D\uDE36","무표정");
        xlat.put("\uD83D\uDE44","머쓱");
        xlat.put("\uD83D\uDE23","찡그리기");
        xlat.put("\uD83D\uDE2E","무표정");
        xlat.put("\uD83E\uDD10","입 다물기");
        xlat.put("\uD83D\uDE2F","호옹");
        xlat.put("\uD83D\uDE2A","졸리네");

        xlat.put("\uD83E\uDD24","침 흘리며 웃기");
        xlat.put("\uD83D\uDE13","머쓱");
        xlat.put("\uD83D\uDE14","절레절레");
        xlat.put("\uD83E\uDD11","오예 돈이다");
        xlat.put("\uD83D\uDE43","미소");
        xlat.put("\uD83D\uDE16","귀여운척");
        xlat.put("\uD83D\uDE1E","시무룩");
        xlat.put("\uD83D\uDE26","무표정");

        xlat.put("\uD83D\uDE27","무표정");
        xlat.put("\uD83D\uDE28","안색이 안 좋음");
        xlat.put("\uD83D\uDE29","에휴");
        xlat.put("\uD83E\uDD2F","머리가 복잡하네");
        xlat.put("\uD83D\uDE2C","살짝 놀람");
        xlat.put("\uD83D\uDE33","부끄");
        xlat.put("\uD83E\uDD2A","약 올리기");

        xlat.put("\uD83E\uDD2C","매우 화남");
        xlat.put("\uD83E\uDD12","감기 걸림");
        xlat.put("\uD83E\uDD15","머리 아픔");
        xlat.put("\uD83E\uDD22","멀미");
        xlat.put("\uD83E\uDD27","코 풀기");
        xlat.put("\uD83E\uDD20","카우보이");
        xlat.put("\uD83E\uDD2D","풋");
        xlat.put("\uD83E\uDDD0","의심");
        xlat.put("\uD83E\uDD13","잘생긴척");
    }
}