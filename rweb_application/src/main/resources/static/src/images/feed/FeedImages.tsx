import * as React from "react";
import {Component} from "react";
import FeedImage from "./FeedImage";
import "./FeedImages.css";
import ImageEntity from "../../model/ImageEntity";

interface ImagesProperties {
    images: ImageEntity[];
}

export default class FeedImages extends Component<ImagesProperties, {}> {

    constructor(props: ImagesProperties, context: any) {
        super(props, context);
    }

    render() {
        let components = this.props.images.map(i => {
            const key = i.links.find(l => l.rel == 'self').href;
            return (
                <FeedImage key={key} image={i}/>
            );
        });
        return (
            <div className="images">
                {components}
            </div>
        );
    }
}