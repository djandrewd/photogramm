import * as React from "react";
import {Component} from "react";

import "./ImagesList.css";
import PlainImage from "./PlainImage";
import ImageEntity from "../../model/ImageEntity";

interface UserImagesProperties {
    images: ImageEntity[]
}

export default class ImagesList extends Component<UserImagesProperties, {}> {

    render(): React.ReactNode {
        const images = this.props.images.map(i => {
            const key = i.links.find(l => l.rel == 'self').href;
            return (
                <PlainImage key={key} image={i}/>
            )
        });

        return (
            <div className="imagesList">
                {images}
            </div>
        );
    }
}