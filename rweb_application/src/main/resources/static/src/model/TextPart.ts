export default class TextPart {
    private _payload: string;
    private _link: boolean;
    private _url: string;

    constructor(payload: string, link: boolean, url: string) {
        this._payload = payload;
        this._link = link;
        this._url = url;
    }

    get payload(): string {
        return this._payload;
    }

    get link(): boolean {
        return this._link;
    }

    get url(): string {
        return this._url;
    }
}