import * as React from "react";
import ImageEntity from "../../model/ImageEntity";

interface ImageValueProperty {
    image: ImageEntity;
}

export default (props: ImageValueProperty) => {
    return (
        <div className="image-pic">
            <img className="image-value" src={props.image.url} alt="Sorry...."/>
        </div>
    )
}

