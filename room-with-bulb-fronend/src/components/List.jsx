import React from 'react';

import axios from 'axios';
import { Link } from 'react-router-dom';

export default class List extends React.Component {
  state = {
    rooms: []
  }

  componentDidMount() {
    axios.get(`/rooms`)
      .then(res => {
        const rooms = res.data;
        this.setState({ rooms });
        console.log(res.data);
      })
  }

  render() {
    return (
      <div>
        <ul>
          <Link to="/create" style={{ 'backgroundColor': '#326B6F', 'color': 'white', 'textDecoration': 'none' }}>
            Create Room
          </Link>
        </ul>
        {this.state.rooms.map(
          room =>
            <Link to={"room/" + room.id}>
              <ul>
                {room.name}({room.country})
            </ul>
            </Link>
        )}
      </div>
    )
  }
}