import Link from "./Link";

export default class Comment {
    private _id: number;
    private _nickname: string;
    private _text: string;
    private _links: Link[];

    constructor(id: number, nickname: string, text: string, links: Link[]) {
        this._id = id;
        this._nickname = nickname;
        this._text = text;
        this._links = links;
    }

    get id(): number {
        return this._id;
    }

    get nickname(): string {
        return this._nickname;
    }

    get text(): string {
        return this._text;
    }

    get links(): Link[] {
        return this._links;
    }

}
