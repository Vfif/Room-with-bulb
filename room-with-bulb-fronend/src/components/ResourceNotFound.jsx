import React from 'react';
import { Link } from 'react-router-dom';

export default class ResourceNotFound extends React.Component {
    render() {
        return (
            <div>
                <ul>
                    <Link to="/" style={{ 'backgroundColor': '#326B6F', 'color': 'white', 'textDecoration': 'none' }}>
                        Back
                    </Link>
                </ul>
                <h2>ResourceNotFound</h2>
            </div>
        )
    }
}