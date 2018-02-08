import * as React from "react";
import {Component} from "react";
import "./FeedImage.css";
import ImageName from "../static/ImageName";
import ImageValue from "../static/ImageValue";
import ImageEntity from "../../model/ImageEntity";
import SocialContainer from "../social/SocialContainer";

interface ImageProperty {
    image: ImageEntity;
}

export default class FeedImage extends Component<ImageProperty, {}> {

    private image: ImageEntity;

    constructor(props: ImageProperty) {
        super(props);
        this.image = props.image;
    }

    render() {
        return (
            <div className="image-container">
                <ImageName image={this.image}/>
                <ImageValue image={this.image}/>
                <SocialContainer image={this.image}/>
            </div>
        )
    }
}

