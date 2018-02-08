import * as React from "react";
import ImageEntity from "../../model/ImageEntity";

interface NamesProperty {
    image: ImageEntity;
}

export default (props: NamesProperty) => {
    return (
        <div className="image-user">
            <label><b>{props.image.nickname}</b></label>
        </div>
    )
}
