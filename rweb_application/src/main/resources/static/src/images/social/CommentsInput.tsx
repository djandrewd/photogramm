import * as React from "react";
import {Component} from "react";

import "./CommentInput.css";
import Comment from "../../model/Comment";
import * as Cookies from "js-cookie";

interface CommentsInputProperties {
    commentAdded: (comment: Comment) => void;
    addCommentURL: string;
}

export default class CommentsInput extends Component<CommentsInputProperties, {}> {

    private commentAdded: (comment: Comment) => void;
    private addCommentURL: string;
    private textAreaInput: HTMLTextAreaElement;
    private csrfToken: string;

    constructor(props: CommentsInputProperties) {
        super(props);
        this.commentAdded = props.commentAdded;
        this.addCommentURL = props.addCommentURL;
        this.csrfToken = Cookies.get('XSRF-TOKEN');
    }

    render(): React.ReactNode {
        return (
            <textarea className="commentValueArea"
                      ref={(element: HTMLTextAreaElement) => {
                          this.textAreaInput = element;
                      }}
                      onKeyPress={(event: any) => {
                          if (event.key === 'Enter') {
                              this.addComment();
                          }
                      }}/>
        );
    }

    private addComment(): void {
        if (this.addCommentURL == null) {
            this.textAreaInput.value = '';
            return;
        }

        let text = this.textAreaInput.value;
        let form = new FormData();
        form.append('text', text);
        fetch(this.addCommentURL, {
            method: 'POST',
            headers: new Headers({
                'X-XSRF-TOKEN': this.csrfToken
            }),
            credentials: "same-origin",
            body: form
        }).then((r) => {
            if (r.status == 200) {
                r.json().then((comment: Comment) => {
                    this.commentAdded(comment);
                    this.textAreaInput.value = '';
                })
            }
        })
            .catch((e: any) => {
                console.log("Cannot save the message.." + e);
                this.textAreaInput.value = '';
            });
    }
}