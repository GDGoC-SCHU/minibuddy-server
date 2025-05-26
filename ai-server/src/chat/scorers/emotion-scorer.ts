import { gemini } from '../../langchain/gemini';

export async function getEmotionScores(text: string): Promise<{ dep: number; anx: number; str: number }> {
    const res = await gemini.invoke([
        [
            'system',
            `Your task is to evaluate user messages and assign numerical scores for depression, anxiety, and stress.
You must respond only in the following JSON format:
{"dep": number, "anx": number, "str": number}
The values must satisfy these constraints:
- Each number must be a real number between 0 and 30, inclusive.
- Do not include any explanation, commentary, or additional text.
- If the input is insufficient for assessment, make your best estimate within the allowed range.`,
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

    // 백틱 제거 및 줄바꿈 정리
    const cleaned = content
        .replace(/```json/g, '')
        .replace(/```/g, '')
        .trim();

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
