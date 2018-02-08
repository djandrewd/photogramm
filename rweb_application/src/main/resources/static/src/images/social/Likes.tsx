import * as React from "react";
import {Component} from "react";

import "./Likes.css";
import ImageEntity from "../../model/ImageEntity";
import * as Cookies from "js-cookie";

interface LikesProperties {

    liked: boolean
    userLiked: () => void
    image: ImageEntity
}

export default class Likes extends Component<LikesProperties, {}> {

    private static LIKED_CLASSNAME: string = 'glyphicon-heart';
    private static DISLIKE_CLASSNAME: string = 'glyphicon-heart-empty';
    private static LIKE_REF_NAME: string = 'like';
    private static DISLIKE_REF_NAME: string = 'dislike';

    private likeUrl: string;
    private dislikeUrl: string;
    private csrfToken: string;

    constructor(props: LikesProperties) {
        super(props);
        let link = this.props.image.links.find(v => v.rel === Likes.LIKE_REF_NAME);
        if (link != null) {
            this.likeUrl = link.href;
        }
        link = this.props.image.links.find(v => v.rel === Likes.DISLIKE_REF_NAME);
        if (link != null) {
            this.dislikeUrl = link.href;
        }
        this.csrfToken = Cookies.get('XSRF-TOKEN');
    }

    render(): React.ReactNode {
        let className = 'glyphicon ' + (this.props.liked ? Likes.LIKED_CLASSNAME : Likes.DISLIKE_CLASSNAME);
        return (
            <div className="likes">
                <button className="btn-like btn btn-link btn-md"
                        onClick={this.socialAction.bind(this)}>
                    <span className={className}/>
                </button>
            </div>
        );
    }

    private socialAction(): void {
        let url = this.props.liked ? this.dislikeUrl : this.likeUrl;
        fetch(url, {
                method: 'POST',
                credentials: "same-origin",
                headers: new Headers({
                    'X-XSRF-TOKEN': this.csrfToken
                }),
            }
        ).then((response: Response) => {
            if (response.status == 200) {
                this.props.userLiked();
            }
        });
    }
}