import React, { Component } from "react";
import { Paper, Avatar } from "@material-ui/core";
import { withStyles } from "@material-ui/core/styles";

const styles = () => ({
  purpleAvatar: {
    color: "#fff",
    backgroundColor: "#00c1d5"
  }
});

class GridItem extends Component {
  state = {};

  render() {
    const { classes } = this.props;

    return (
      <Paper
        style={{
          width: 150,
          height: 150,
          overflow: "auto",
          display: "none"
        }}
      >
      
      </Paper>
    );
  }
}

export default withStyles(styles, { withTheme: true })(GridItem);
