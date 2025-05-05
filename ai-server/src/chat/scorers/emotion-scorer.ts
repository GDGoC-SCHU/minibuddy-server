import { gemini } from '../../langchain/gemini';

export async function getEmotionScores(text: string): Promise<{ dep: number; anx: number; str: number }> {
    const res = await gemini.invoke([
        [
            'system',
            '다음 메시지에서 우울, 불안, 스트레스 점수를 추정해줘. 반드시 이 JSON 형식으로만 응답해: {"dep": number, "anx": number, "str": number}',
        ],
        ['human', text],
    ]);

    const content = typeof res.content === 'string' ? res.content : res?.text ?? '';

    console.log('[Gemini Raw Response]', content); // 디버깅 로그 추가

    // JSON 부분만 추출 (중괄호 블록 기준으로 추출)
    const jsonMatch = content.match(/\{[\s\S]*?\}/);

    if (!jsonMatch) {
        throw new Error('No valid JSON object found in Gemini response');
    }

    try {
        const parsed = JSON.parse(jsonMatch[0]);
        return {
            dep: parsed.dep ?? 0,
            anx: parsed.anx ?? 0,
            str: parsed.str ?? 0,
        };
    } catch (err) {
        console.error('[getEmotionScores] JSON parse error:', err);
        throw new Error('Invalid JSON format from Gemini');
    }
}
