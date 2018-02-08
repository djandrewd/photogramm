import Link from "./Link";
import TextPart from "./TextPart";

export default class ImageEntity {

    private _name: TextPart[];
    private _url: string;
    private _likes: number;
    private _nickname: string;
    private _liked: boolean;
    private _links: Link[];

    constructor(name: TextPart[], likes: number, url: string, nickname: string, liked: boolean, links: Link[]) {
        this._name = name;
        this._likes = likes;
        this._url = url;
        this._nickname = nickname;
        this._links = links;
        this._liked = liked;
    }

    get liked(): boolean {
        return this._liked;
    }

    get name(): TextPart[] {
        return this._name;
    }

    get likes(): number {
        return this._likes;
    }

    get url(): string {
        return this._url;
    }

    get nickname(): string {
        return this._nickname;
    }

    get links(): Link[] {
        return this._links;
    }
}
