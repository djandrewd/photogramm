import * as React from "react";
import {Component} from "react";
import "./SocialContainer.css";
import ImageEntity from "../../model/ImageEntity";
import Likes from "./Likes";
import LikesText from "./LikesText";
import Descriptions from "./Descriptions";
import Comments from "./Comments";

interface SocialProperties {
    image: ImageEntity;
}

interface SocialState {
    likes: number;
    liked: boolean;
}

export default class SocialContainer extends Component<SocialProperties, SocialState> {

    private updateStateUrl: string;

    constructor(props: SocialProperties, context: any) {
        super(props, context);
        let link = this.props.image.links.find(v => v.rel === 'self');
        if (link != null) {
            this.updateStateUrl = link.href;
        }
        this.state = {
            liked: props.image.liked,
            likes: props.image.likes
        }
    }

    render(): React.ReactNode {
        return (
            <div className="image-social">
                <Likes liked={this.state.liked} userLiked={this.userLiked.bind(this)}
                       image={this.props.image}/>
                <LikesText likes={this.state.likes}/>
                <Descriptions image={this.props.image}/>
                <Comments image={this.props.image}/>
            </div>
        );
    }

    public userLiked(): void {
        let prev = this.state.liked;
        fetch(this.updateStateUrl, {
            credentials: "same-origin"
        }).then((r: Response) => r.json())
            .then((image: ImageEntity) => {
                this.setState({
                    liked: !prev,
                    likes: image.likes
                });
            }).catch((e) => {
            console.log("Error happens: " + e);
        })

    }

}