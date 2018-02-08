import ImageEntity from "./ImageEntity";
import Link from "./Link";

export class HistoryAction {
    private _nickname: string;
    private _action: number;
    private _image: ImageEntity;


    constructor(nickname: string, action: number, image: ImageEntity) {
        this._nickname = nickname;
        this._action = action;
        this._image = image;
    }


    get nickname(): string {
        return this._nickname;
    }

    get action(): number {
        return this._action;
    }

    get image(): ImageEntity {
        return this._image;
    }
}

export default class HistoryActions {
    private _actions: HistoryAction[];
    private _links: Link[];


    constructor(actions: HistoryAction[], links: Link[]) {
        this._actions = actions;
        this._links = links;
    }

    get actions(): HistoryAction[] {
        return this._actions;
    }

    get links(): Link[] {
        return this._links;
    }
}