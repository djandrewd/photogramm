import * as React from "react";
import Comment from "../../model/Comment";

interface CommentTextProperties {
    comment: Comment
}

export default (props: CommentTextProperties) => {
    return (
        <div className="commentText">
            <b>{props.comment.nickname}</b> {props.comment.text}
        </div>
    )
}