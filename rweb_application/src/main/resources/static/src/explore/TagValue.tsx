import * as React from "react";
import {ExploreTagsProperties} from "./ExploreTags";

import "./TagValue.css";

export default (props: ExploreTagsProperties) => {
    return <div className="tagName"><h2>#{props.tagName}</h2></div>
}