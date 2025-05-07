package com.minibuddy.feature.chat.domain.enums;

import com.minibuddy.feature.chat.domain.EmotionScores;

import java.util.ArrayList;
import java.util.List;

public enum EmotionType {
    DEPRESSION, ANXIETY, STRESS, NORMAL,
    ;

    public static EmotionType dominantEmotionType(EmotionScores scores) {
        int dep = scores.getDepressionScore();
        int anx = scores.getAnxietyScore();
        int str = scores.getStressScore();

        int max = Math.max(Math.max(dep, anx), str);

        // 최대값과 일치하는 감정 타입 수집
        List<EmotionType> maxTypes = new ArrayList<>();
        if (dep == max) maxTypes.add(DEPRESSION);
        if (anx == max) maxTypes.add(ANXIETY);
        if (str == max) maxTypes.add(STRESS);

        // 단일 최대값이면 해당 타입 반환
        // 동점이거나 모든 점수가 0인 경우 NORMAL 반환
        // 일단은..
        return (maxTypes.size() == 1) ? maxTypes.get(0) : NORMAL;
    }
}
