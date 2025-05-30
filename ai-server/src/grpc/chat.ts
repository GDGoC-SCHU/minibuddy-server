// @generated by protobuf-ts 2.10.0
// @generated from protobuf file "chat.proto" (package "chat", syntax proto3)
// tslint:disable
import { ServiceType } from "@protobuf-ts/runtime-rpc";
import type { BinaryWriteOptions } from "@protobuf-ts/runtime";
import type { IBinaryWriter } from "@protobuf-ts/runtime";
import { WireType } from "@protobuf-ts/runtime";
import type { BinaryReadOptions } from "@protobuf-ts/runtime";
import type { IBinaryReader } from "@protobuf-ts/runtime";
import { UnknownFieldHandler } from "@protobuf-ts/runtime";
import type { PartialMessage } from "@protobuf-ts/runtime";
import { reflectionMergePartial } from "@protobuf-ts/runtime";
import { MessageType } from "@protobuf-ts/runtime";
/**
 * -------- Request message --------
 *
 * @generated from protobuf message chat.ChatRequest
 */
export interface ChatRequest {
    /**
     * @generated from protobuf field: string user_id = 1;
     */
    userId: string;
    /**
     * @generated from protobuf field: string message = 2;
     */
    message: string;
}
/**
 * @generated from protobuf message chat.MemoryAnswerRequest
 */
export interface MemoryAnswerRequest {
    /**
     * @generated from protobuf field: string user_id = 1;
     */
    userId: string;
    /**
     * @generated from protobuf field: string answer = 2;
     */
    answer: string;
    /**
     * @generated from protobuf field: string question_id = 3;
     */
    questionId: string;
}
/**
 * -------- Response message --------
 *
 * @generated from protobuf message chat.ChatResponse
 */
export interface ChatResponse {
    /**
     * @generated from protobuf field: string reply = 1;
     */
    reply: string;
    /**
     * @generated from protobuf field: int32 dep_score = 2;
     */
    depScore: number;
    /**
     * @generated from protobuf field: int32 anx_score = 3;
     */
    anxScore: number;
    /**
     * @generated from protobuf field: int32 str_score = 4;
     */
    strScore: number;
}
/**
 * @generated from protobuf message chat.MemoryQuestionResponse
 */
export interface MemoryQuestionResponse {
    /**
     * @generated from protobuf field: string question = 1;
     */
    question: string;
    /**
     * @generated from protobuf field: int32 dep_score = 2;
     */
    depScore: number;
}
/**
 * @generated from protobuf message chat.MemoryAnswerResult
 */
export interface MemoryAnswerResult {
    /**
     * @generated from protobuf field: string reply = 1;
     */
    reply: string;
    /**
     * @generated from protobuf field: int32 mci_score = 2;
     */
    mciScore: number;
    /**
     * @generated from protobuf field: int32 dep_score = 3;
     */
    depScore: number;
    /**
     * @generated from protobuf field: string mci_reason = 4;
     */
    mciReason: string;
}
// @generated message type with reflection information, may provide speed optimized methods
class ChatRequest$Type extends MessageType<ChatRequest> {
    constructor() {
        super("chat.ChatRequest", [
            { no: 1, name: "user_id", kind: "scalar", T: 9 /*ScalarType.STRING*/ },
            { no: 2, name: "message", kind: "scalar", T: 9 /*ScalarType.STRING*/ }
        ]);
    }
    create(value?: PartialMessage<ChatRequest>): ChatRequest {
        const message = globalThis.Object.create((this.messagePrototype!));
        message.userId = "";
        message.message = "";
        if (value !== undefined)
            reflectionMergePartial<ChatRequest>(this, message, value);
        return message;
    }
    internalBinaryRead(reader: IBinaryReader, length: number, options: BinaryReadOptions, target?: ChatRequest): ChatRequest {
        let message = target ?? this.create(), end = reader.pos + length;
        while (reader.pos < end) {
            let [fieldNo, wireType] = reader.tag();
            switch (fieldNo) {
                case /* string user_id */ 1:
                    message.userId = reader.string();
                    break;
                case /* string message */ 2:
                    message.message = reader.string();
                    break;
                default:
                    let u = options.readUnknownField;
                    if (u === "throw")
                        throw new globalThis.Error(`Unknown field ${fieldNo} (wire type ${wireType}) for ${this.typeName}`);
                    let d = reader.skip(wireType);
                    if (u !== false)
                        (u === true ? UnknownFieldHandler.onRead : u)(this.typeName, message, fieldNo, wireType, d);
            }
        }
        return message;
    }
    internalBinaryWrite(message: ChatRequest, writer: IBinaryWriter, options: BinaryWriteOptions): IBinaryWriter {
        /* string user_id = 1; */
        if (message.userId !== "")
            writer.tag(1, WireType.LengthDelimited).string(message.userId);
        /* string message = 2; */
        if (message.message !== "")
            writer.tag(2, WireType.LengthDelimited).string(message.message);
        let u = options.writeUnknownFields;
        if (u !== false)
            (u == true ? UnknownFieldHandler.onWrite : u)(this.typeName, message, writer);
        return writer;
    }
}
/**
 * @generated MessageType for protobuf message chat.ChatRequest
 */
export const ChatRequest = new ChatRequest$Type();
// @generated message type with reflection information, may provide speed optimized methods
class MemoryAnswerRequest$Type extends MessageType<MemoryAnswerRequest> {
    constructor() {
        super("chat.MemoryAnswerRequest", [
            { no: 1, name: "user_id", kind: "scalar", T: 9 /*ScalarType.STRING*/ },
            { no: 2, name: "answer", kind: "scalar", T: 9 /*ScalarType.STRING*/ },
            { no: 3, name: "question_id", kind: "scalar", T: 9 /*ScalarType.STRING*/ }
        ]);
    }
    create(value?: PartialMessage<MemoryAnswerRequest>): MemoryAnswerRequest {
        const message = globalThis.Object.create((this.messagePrototype!));
        message.userId = "";
        message.answer = "";
        message.questionId = "";
        if (value !== undefined)
            reflectionMergePartial<MemoryAnswerRequest>(this, message, value);
        return message;
    }
    internalBinaryRead(reader: IBinaryReader, length: number, options: BinaryReadOptions, target?: MemoryAnswerRequest): MemoryAnswerRequest {
        let message = target ?? this.create(), end = reader.pos + length;
        while (reader.pos < end) {
            let [fieldNo, wireType] = reader.tag();
            switch (fieldNo) {
                case /* string user_id */ 1:
                    message.userId = reader.string();
                    break;
                case /* string answer */ 2:
                    message.answer = reader.string();
                    break;
                case /* string question_id */ 3:
                    message.questionId = reader.string();
                    break;
                default:
                    let u = options.readUnknownField;
                    if (u === "throw")
                        throw new globalThis.Error(`Unknown field ${fieldNo} (wire type ${wireType}) for ${this.typeName}`);
                    let d = reader.skip(wireType);
                    if (u !== false)
                        (u === true ? UnknownFieldHandler.onRead : u)(this.typeName, message, fieldNo, wireType, d);
            }
        }
        return message;
    }
    internalBinaryWrite(message: MemoryAnswerRequest, writer: IBinaryWriter, options: BinaryWriteOptions): IBinaryWriter {
        /* string user_id = 1; */
        if (message.userId !== "")
            writer.tag(1, WireType.LengthDelimited).string(message.userId);
        /* string answer = 2; */
        if (message.answer !== "")
            writer.tag(2, WireType.LengthDelimited).string(message.answer);
        /* string question_id = 3; */
        if (message.questionId !== "")
            writer.tag(3, WireType.LengthDelimited).string(message.questionId);
        let u = options.writeUnknownFields;
        if (u !== false)
            (u == true ? UnknownFieldHandler.onWrite : u)(this.typeName, message, writer);
        return writer;
    }
}
/**
 * @generated MessageType for protobuf message chat.MemoryAnswerRequest
 */
export const MemoryAnswerRequest = new MemoryAnswerRequest$Type();
// @generated message type with reflection information, may provide speed optimized methods
class ChatResponse$Type extends MessageType<ChatResponse> {
    constructor() {
        super("chat.ChatResponse", [
            { no: 1, name: "reply", kind: "scalar", T: 9 /*ScalarType.STRING*/ },
            { no: 2, name: "dep_score", kind: "scalar", T: 5 /*ScalarType.INT32*/ },
            { no: 3, name: "anx_score", kind: "scalar", T: 5 /*ScalarType.INT32*/ },
            { no: 4, name: "str_score", kind: "scalar", T: 5 /*ScalarType.INT32*/ }
        ]);
    }
    create(value?: PartialMessage<ChatResponse>): ChatResponse {
        const message = globalThis.Object.create((this.messagePrototype!));
        message.reply = "";
        message.depScore = 0;
        message.anxScore = 0;
        message.strScore = 0;
        if (value !== undefined)
            reflectionMergePartial<ChatResponse>(this, message, value);
        return message;
    }
    internalBinaryRead(reader: IBinaryReader, length: number, options: BinaryReadOptions, target?: ChatResponse): ChatResponse {
        let message = target ?? this.create(), end = reader.pos + length;
        while (reader.pos < end) {
            let [fieldNo, wireType] = reader.tag();
            switch (fieldNo) {
                case /* string reply */ 1:
                    message.reply = reader.string();
                    break;
                case /* int32 dep_score */ 2:
                    message.depScore = reader.int32();
                    break;
                case /* int32 anx_score */ 3:
                    message.anxScore = reader.int32();
                    break;
                case /* int32 str_score */ 4:
                    message.strScore = reader.int32();
                    break;
                default:
                    let u = options.readUnknownField;
                    if (u === "throw")
                        throw new globalThis.Error(`Unknown field ${fieldNo} (wire type ${wireType}) for ${this.typeName}`);
                    let d = reader.skip(wireType);
                    if (u !== false)
                        (u === true ? UnknownFieldHandler.onRead : u)(this.typeName, message, fieldNo, wireType, d);
            }
        }
        return message;
    }
    internalBinaryWrite(message: ChatResponse, writer: IBinaryWriter, options: BinaryWriteOptions): IBinaryWriter {
        /* string reply = 1; */
        if (message.reply !== "")
            writer.tag(1, WireType.LengthDelimited).string(message.reply);
        /* int32 dep_score = 2; */
        if (message.depScore !== 0)
            writer.tag(2, WireType.Varint).int32(message.depScore);
        /* int32 anx_score = 3; */
        if (message.anxScore !== 0)
            writer.tag(3, WireType.Varint).int32(message.anxScore);
        /* int32 str_score = 4; */
        if (message.strScore !== 0)
            writer.tag(4, WireType.Varint).int32(message.strScore);
        let u = options.writeUnknownFields;
        if (u !== false)
            (u == true ? UnknownFieldHandler.onWrite : u)(this.typeName, message, writer);
        return writer;
    }
}
/**
 * @generated MessageType for protobuf message chat.ChatResponse
 */
export const ChatResponse = new ChatResponse$Type();
// @generated message type with reflection information, may provide speed optimized methods
class MemoryQuestionResponse$Type extends MessageType<MemoryQuestionResponse> {
    constructor() {
        super("chat.MemoryQuestionResponse", [
            { no: 1, name: "question", kind: "scalar", T: 9 /*ScalarType.STRING*/ },
            { no: 2, name: "dep_score", kind: "scalar", T: 5 /*ScalarType.INT32*/ }
        ]);
    }
    create(value?: PartialMessage<MemoryQuestionResponse>): MemoryQuestionResponse {
        const message = globalThis.Object.create((this.messagePrototype!));
        message.question = "";
        message.depScore = 0;
        if (value !== undefined)
            reflectionMergePartial<MemoryQuestionResponse>(this, message, value);
        return message;
    }
    internalBinaryRead(reader: IBinaryReader, length: number, options: BinaryReadOptions, target?: MemoryQuestionResponse): MemoryQuestionResponse {
        let message = target ?? this.create(), end = reader.pos + length;
        while (reader.pos < end) {
            let [fieldNo, wireType] = reader.tag();
            switch (fieldNo) {
                case /* string question */ 1:
                    message.question = reader.string();
                    break;
                case /* int32 dep_score */ 2:
                    message.depScore = reader.int32();
                    break;
                default:
                    let u = options.readUnknownField;
                    if (u === "throw")
                        throw new globalThis.Error(`Unknown field ${fieldNo} (wire type ${wireType}) for ${this.typeName}`);
                    let d = reader.skip(wireType);
                    if (u !== false)
                        (u === true ? UnknownFieldHandler.onRead : u)(this.typeName, message, fieldNo, wireType, d);
            }
        }
        return message;
    }
    internalBinaryWrite(message: MemoryQuestionResponse, writer: IBinaryWriter, options: BinaryWriteOptions): IBinaryWriter {
        /* string question = 1; */
        if (message.question !== "")
            writer.tag(1, WireType.LengthDelimited).string(message.question);
        /* int32 dep_score = 2; */
        if (message.depScore !== 0)
            writer.tag(2, WireType.Varint).int32(message.depScore);
        let u = options.writeUnknownFields;
        if (u !== false)
            (u == true ? UnknownFieldHandler.onWrite : u)(this.typeName, message, writer);
        return writer;
    }
}
/**
 * @generated MessageType for protobuf message chat.MemoryQuestionResponse
 */
export const MemoryQuestionResponse = new MemoryQuestionResponse$Type();
// @generated message type with reflection information, may provide speed optimized methods
class MemoryAnswerResult$Type extends MessageType<MemoryAnswerResult> {
    constructor() {
        super("chat.MemoryAnswerResult", [
            { no: 1, name: "reply", kind: "scalar", T: 9 /*ScalarType.STRING*/ },
            { no: 2, name: "mci_score", kind: "scalar", T: 5 /*ScalarType.INT32*/ },
            { no: 3, name: "dep_score", kind: "scalar", T: 5 /*ScalarType.INT32*/ },
            { no: 4, name: "mci_reason", kind: "scalar", T: 9 /*ScalarType.STRING*/ }
        ]);
    }
    create(value?: PartialMessage<MemoryAnswerResult>): MemoryAnswerResult {
        const message = globalThis.Object.create((this.messagePrototype!));
        message.reply = "";
        message.mciScore = 0;
        message.depScore = 0;
        message.mciReason = "";
        if (value !== undefined)
            reflectionMergePartial<MemoryAnswerResult>(this, message, value);
        return message;
    }
    internalBinaryRead(reader: IBinaryReader, length: number, options: BinaryReadOptions, target?: MemoryAnswerResult): MemoryAnswerResult {
        let message = target ?? this.create(), end = reader.pos + length;
        while (reader.pos < end) {
            let [fieldNo, wireType] = reader.tag();
            switch (fieldNo) {
                case /* string reply */ 1:
                    message.reply = reader.string();
                    break;
                case /* int32 mci_score */ 2:
                    message.mciScore = reader.int32();
                    break;
                case /* int32 dep_score */ 3:
                    message.depScore = reader.int32();
                    break;
                case /* string mci_reason */ 4:
                    message.mciReason = reader.string();
                    break;
                default:
                    let u = options.readUnknownField;
                    if (u === "throw")
                        throw new globalThis.Error(`Unknown field ${fieldNo} (wire type ${wireType}) for ${this.typeName}`);
                    let d = reader.skip(wireType);
                    if (u !== false)
                        (u === true ? UnknownFieldHandler.onRead : u)(this.typeName, message, fieldNo, wireType, d);
            }
        }
        return message;
    }
    internalBinaryWrite(message: MemoryAnswerResult, writer: IBinaryWriter, options: BinaryWriteOptions): IBinaryWriter {
        /* string reply = 1; */
        if (message.reply !== "")
            writer.tag(1, WireType.LengthDelimited).string(message.reply);
        /* int32 mci_score = 2; */
        if (message.mciScore !== 0)
            writer.tag(2, WireType.Varint).int32(message.mciScore);
        /* int32 dep_score = 3; */
        if (message.depScore !== 0)
            writer.tag(3, WireType.Varint).int32(message.depScore);
        /* string mci_reason = 4; */
        if (message.mciReason !== "")
            writer.tag(4, WireType.LengthDelimited).string(message.mciReason);
        let u = options.writeUnknownFields;
        if (u !== false)
            (u == true ? UnknownFieldHandler.onWrite : u)(this.typeName, message, writer);
        return writer;
    }
}
/**
 * @generated MessageType for protobuf message chat.MemoryAnswerResult
 */
export const MemoryAnswerResult = new MemoryAnswerResult$Type();
/**
 * @generated ServiceType for protobuf service chat.ChatAiService
 */
export const ChatAiService = new ServiceType("chat.ChatAiService", [
    { name: "GetChatResponse", options: {}, I: ChatRequest, O: ChatResponse },
    { name: "GetMemoryQuestion", options: {}, I: ChatRequest, O: MemoryQuestionResponse },
    { name: "EvaluateMemoryAnswer", options: {}, I: MemoryAnswerRequest, O: MemoryAnswerResult }
]);
