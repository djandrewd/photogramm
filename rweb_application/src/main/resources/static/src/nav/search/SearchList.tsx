import * as React from "react";
import {Component} from "react";
import UserEntity from "../../model/UserEntity";
import {Link} from "react-router-dom";

interface SearchListProperties {
    users: UserEntity[];
}

export default class SearchList extends Component<SearchListProperties, {}> {

    constructor(props: SearchListProperties) {
        super(props);
    }

    render(): React.ReactNode {
        const users = this.props.users.map(u => {
            return (
                <Link className="list-group-item u-link" key={u.nickname} to={u.nickname}>{u.username}</Link>
            )
        });

        return (<div className="search-results list-group">{users}</div>);
    }
}