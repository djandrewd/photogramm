import * as React from "react";

import "./Description.css";
import ImageEntity from "../../model/ImageEntity";
import TextPart from "../../model/TextPart";
import {Link} from "react-router-dom";

interface DescriptionProperties {
    image: ImageEntity
}

export default (props: DescriptionProperties) => {
    return (
        <div className="description">
            <b>{props.image.nickname} </b>
            <span>
                {buildText(props.image.name)}
            </span>
        </div>
    );
}

function buildText(parts: TextPart[]): any {
    let counter = 0;
    return parts.map(t => {
        if (t.link) {
            return (<span key={counter++}><Link to={t.url}>{t.payload}</Link></span>);
        }
        return (<span key={counter++}>{t.payload}</span>);
    });

}