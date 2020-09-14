import React, { Fragment } from "react";
import Button from "@material-ui/core/Button";
import { withStyles } from "@material-ui/core/styles";

// eslint-disable-next-line
const styles =  () =>  ({
  button: {
    margin: "30px 10px 30px 0px",
    boxShadow: "none",
    background: "#00c1d5",
    color: "white",
    "&:hover": {
      color: "#00c1d5"
    },
  }
});

const SubmitCancel = ({ classes, onCancelClick }) => (
  <Fragment>
    <Button
      type="submit"
      size="small"
      className={classes.button}
      variant="raised"
      color="primary"
    >
      Submit
    </Button>

    <Button
      size="small"
      className={classes.button}
      variant="raised"
      color="default"
      onClick={onCancelClick}
    >
      Cancel
    </Button>
  </Fragment>
);

export default withStyles(styles, { withTheme: true })(SubmitCancel);
