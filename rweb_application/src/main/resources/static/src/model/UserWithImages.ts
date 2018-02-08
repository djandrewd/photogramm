import ImageEntity from "./ImageEntity";
import Link from "./Link";

export default class UserWithImages {

    private _nickname: string;
    private _images: ImageEntity[];
    private _links: Link[];

    constructor(nickname: string, images: ImageEntity[], links: Link[]) {
        this._nickname = nickname;
        this._images = images;
        this._links = links;
    }

    get nickname(): string {
        return this._nickname;
    }

    get images(): ImageEntity[] {
        return this._images;
    }

    get links(): Link[] {
        return this._links;
    }
}