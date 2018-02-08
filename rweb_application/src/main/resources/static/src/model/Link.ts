export default class Link {
    private _rel: string;
    private _href: string;

    constructor(rel: string, href: string) {
        this._rel = rel;
        this._href = href;
    }

    get rel(): string {
        return this._rel;
    }

    get href(): string {
        return this._href;
    }
}