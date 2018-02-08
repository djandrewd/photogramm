import ImageEntity from "./ImageEntity";
import Link from "./Link";

export default class UserImagesEntity {
    private _images: ImageEntity[];
    private _links: Link[];

    constructor(images: ImageEntity[], links: Link[]) {
        this._images = images;
        this._links = links;
    }

    get images(): ImageEntity[] {
        return this._images;
    }


    get links(): Link[] {
        return this._links;
    }
}