// @generated by protobuf-ts 2.10.0
// @generated from protobuf file "chat.proto" (package "chat", syntax proto3)
// tslint:disable
import type { RpcTransport } from "@protobuf-ts/runtime-rpc";
import type { ServiceInfo } from "@protobuf-ts/runtime-rpc";
import { ChatAiService } from "./chat";
import type { MemoryAnswerResult } from "./chat";
import type { MemoryAnswerRequest } from "./chat";
import type { MemoryQuestionResponse } from "./chat";
import { stackIntercept } from "@protobuf-ts/runtime-rpc";
import type { ChatResponse } from "./chat";
import type { ChatRequest } from "./chat";
import type { UnaryCall } from "@protobuf-ts/runtime-rpc";
import type { RpcOptions } from "@protobuf-ts/runtime-rpc";
/**
 * -------- Service --------
 *
 * @generated from protobuf service chat.ChatAiService
 */
export interface IChatAiServiceClient {
    /**
     * @generated from protobuf rpc: GetChatResponse(chat.ChatRequest) returns (chat.ChatResponse);
     */
    getChatResponse(input: ChatRequest, options?: RpcOptions): UnaryCall<ChatRequest, ChatResponse>;
    /**
     * @generated from protobuf rpc: GetMemoryQuestion(chat.ChatRequest) returns (chat.MemoryQuestionResponse);
     */
    getMemoryQuestion(input: ChatRequest, options?: RpcOptions): UnaryCall<ChatRequest, MemoryQuestionResponse>;
    /**
     * @generated from protobuf rpc: EvaluateMemoryAnswer(chat.MemoryAnswerRequest) returns (chat.MemoryAnswerResult);
     */
    evaluateMemoryAnswer(input: MemoryAnswerRequest, options?: RpcOptions): UnaryCall<MemoryAnswerRequest, MemoryAnswerResult>;
}
/**
 * -------- Service --------
 *
 * @generated from protobuf service chat.ChatAiService
 */
export class ChatAiServiceClient implements IChatAiServiceClient, ServiceInfo {
    typeName = ChatAiService.typeName;
    methods = ChatAiService.methods;
    options = ChatAiService.options;
    constructor(private readonly _transport: RpcTransport) {
    }
    /**
     * @generated from protobuf rpc: GetChatResponse(chat.ChatRequest) returns (chat.ChatResponse);
     */
    getChatResponse(input: ChatRequest, options?: RpcOptions): UnaryCall<ChatRequest, ChatResponse> {
        const method = this.methods[0], opt = this._transport.mergeOptions(options);
        return stackIntercept<ChatRequest, ChatResponse>("unary", this._transport, method, opt, input);
    }
    /**
     * @generated from protobuf rpc: GetMemoryQuestion(chat.ChatRequest) returns (chat.MemoryQuestionResponse);
     */
    getMemoryQuestion(input: ChatRequest, options?: RpcOptions): UnaryCall<ChatRequest, MemoryQuestionResponse> {
        const method = this.methods[1], opt = this._transport.mergeOptions(options);
        return stackIntercept<ChatRequest, MemoryQuestionResponse>("unary", this._transport, method, opt, input);
    }
    /**
     * @generated from protobuf rpc: EvaluateMemoryAnswer(chat.MemoryAnswerRequest) returns (chat.MemoryAnswerResult);
     */
    evaluateMemoryAnswer(input: MemoryAnswerRequest, options?: RpcOptions): UnaryCall<MemoryAnswerRequest, MemoryAnswerResult> {
        const method = this.methods[2], opt = this._transport.mergeOptions(options);
        return stackIntercept<MemoryAnswerRequest, MemoryAnswerResult>("unary", this._transport, method, opt, input);
    }
}
