import Link from "./Link";

export default class UserEntity {
    private _nickname: string;
    private _username: string;
    private _posts: number;
    private _self: boolean;
    private _subscribed: boolean;
    private _subscribers: number;
    private _subscriptions: number;
    private _links: Link[];

    constructor(nickname: string, username: string, posts: number, self: boolean, subscribed: boolean, subscribers: number, subscriptions: number, links: Link[]) {
        this._nickname = nickname;
        this._username = username;
        this._posts = posts;
        this._self = self;
        this._subscribed = subscribed;
        this._subscribers = subscribers;
        this._subscriptions = subscriptions;
        this._links = links;
    }

    get nickname(): string {
        return this._nickname;
    }

    get username(): string {
        return this._username;
    }

    get posts(): number {
        return this._posts;
    }

    get self(): boolean {
        return this._self;
    }

    get subscribed(): boolean {
        return this._subscribed;
    }

    get subscribers(): number {
        return this._subscribers;
    }

    get subscriptions(): number {
        return this._subscriptions;
    }

    get links(): Link[] {
        return this._links;
    }
}