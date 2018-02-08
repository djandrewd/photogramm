import * as React from "react";
import {Component} from "react";

import "./Comments.css";
import ImageEntity from "../../model/ImageEntity";
import Comment from "../../model/Comment";
import CommentText from "./CommentText";
import CommentsInput from "./CommentsInput";
import Link from "../../model/Link";

interface CommentsProperties {
    image: ImageEntity;
}

interface CommentsState {
    comments: Comment[];
}

export default class Comments extends Component<CommentsProperties, CommentsState> {
    private static MAX_SIZE: number = 5;

    private listCommentsURL: string;
    private addCommentsURL: string;

    constructor(props: CommentsProperties) {
        super(props);
        let link = this.props.image.links.find(v => v.rel === 'comment');
        if (link != null) {
            this.addCommentsURL = link.href;
        }
        link = this.props.image.links.find(v => v.rel === 'listComments');
        if (link != null) {
            this.listCommentsURL = link.href;
        }
        this.state = {
            comments: []
        }
    }

    componentDidMount() {
        this.fetchComments();
    }

    render(): React.ReactNode {
        const texts = this.state.comments.map((v : Comment)=> {
            const key = v.links.find(l => l.rel == "self").href;
            return (
                <CommentText comment={v} key={key}/>
            );
        });

        return (
            <div className="comments">
                {texts}
                <CommentsInput addCommentURL={this.addCommentsURL}
                               commentAdded={this.commentAdded.bind(this)}/>
            </div>
        );
    }

    public commentAdded(comment: Comment) {
        this.fetchComments();
    }

    private fetchComments() {
        if (this.listCommentsURL != null) {
            fetch(this.listCommentsURL, {
                credentials: "same-origin"
            }).then(response => response.json()
            ).then((comments: Comment[]) => {
                this.setState({
                    comments: comments
                });
            })
                .catch((e) => {
                    console.log("Unfortunately.... " + e);
                })
        }
    }
}