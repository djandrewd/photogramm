import * as React from "react";
import {Component} from "react";
import "./ExploreTags.css";
import {RouteComponentProps} from "react-router";
import ImageEntity from "../model/ImageEntity";
import TagValue from "./TagValue";
import ImagesList from "../images/list/ImagesList";
import Navigation from "../nav/Navigation";
import UserWithImages from "../model/UserWithImages";

export interface ExploreTagsProperties {
    tagName: string
}

interface ExploreTagsState {
    images: ImageEntity[]
    nickname: string
}

export default class ExploreTags extends Component<RouteComponentProps<ExploreTagsProperties>, ExploreTagsState> {
    private static FETCH_TAG_IMAGES: string = "/tagged/";

    private tagName: string;

    constructor(props: RouteComponentProps<ExploreTagsProperties>) {
        super(props);
        this.tagName = props.match.params.tagName;
        this.state = {
            images: [],
            nickname: ''
        }
    }

    componentDidMount() {
        this.refreshImages();
    }

    render(): React.ReactNode {
        return (
            <div className="exploreTag">
                <Navigation refreshFeed={() => this.refreshImages.bind(this)}
                            nickname={this.state.nickname}/>
                <TagValue tagName={this.tagName}/>
                <ImagesList images={this.state.images}/>
            </div>
        );
    }

    private refreshImages() {
        fetch(ExploreTags.FETCH_TAG_IMAGES + this.tagName, {
            credentials: "same-origin"
        })
            .then((r) => {
                if (r.ok) {
                    r.json()
                        .then((u: UserWithImages[]) => {
                                this.setState({
                                    images: u.length > 0 ? u[0].images : [],
                                    nickname: u.length > 0 ? u[0].nickname : ''
                                })
                            }
                        )
                }
            }).catch((e) => {
            console.log("Some error occurred: " + e);
        })
    }
}
