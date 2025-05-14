import { ChatGoogleGenerativeAI } from '@langchain/google-genai';

export const gemini = new ChatGoogleGenerativeAI({
    apiKey: process.env.GOOGLE_API_KEY!,
    model: "gemini-1.5-pro",
    maxOutputTokens: 2048,
});