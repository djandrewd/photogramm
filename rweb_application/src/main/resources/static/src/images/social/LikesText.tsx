import * as React from "react";

import "./LikesText.css";

interface LikesTextProperties {
    likes: number
}

export default (props: LikesTextProperties) => {
    return (
        <div className="likesText">
            <b>{props.likes} likes</b>
        </div>);
}