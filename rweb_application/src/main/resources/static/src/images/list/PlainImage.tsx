import * as React from "react";
import ImageEntity from "../../model/ImageEntity";

import "./PlainImage.css";

interface ImageEntityProperties {
    image: ImageEntity
}

export default (props: ImageEntityProperties) => {
    return (
        <div className="plainImageContainer">
            <img className="plainImageValue" src={props.image.url}/>
        </div>
    )
}