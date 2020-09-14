import React, { Component } from "react";
import classNames from "classnames";
import { withStyles } from "@material-ui/core/styles";
import { ListItem, ListItemIcon, ListItemText } from "@material-ui/core";

// eslint-disable-next-line
const styles = theme => ({
  listItem: {
    "&:hover": {
      backgroundColor: "#00c1d5",
      "& $primary, & $icon": {
        color: theme.palette.common.white
      }
    }
  },
  primary: {},
  icon: {
    width: 19
  },
  selected: {
    backgroundColor: "#00000014",
    borderLeft: "5px solid #00c1d5",
    paddingLeft: 20
  },
  normal: {
    backgroundColor: "white"
  }
});

class SidebarMenu extends Component {
  state = {};

  getClassName = () => {
    const { isSelected, classes } = this.props;

    if (isSelected) return classNames(classes.listItem, classes.selected);

    return classNames(classes.listItem, classes.normal);
  };

  render() {
    const { classes } = this.props;

    return (
      <ListItem
        button
        dense
        onClick={this.props.onClick}
        className={this.getClassName()}
      >
        <ListItemIcon className={classes.icon}>{this.props.icon}</ListItemIcon>
        <ListItemText
          style={{ padding: 2 }}
          classes={{ primary: classes.primary }}
          primary={this.props.text}
        />
      </ListItem>
    );
  }
}

export default withStyles(styles, { withTheme: true })(SidebarMenu);
