import React from "react";
import Button from "@material-ui/core/Button";
import  {Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle
} from "@material-ui/core";

const YesNo = props => (
  <Dialog
    open={props.open}
    aria-labelledby="alert-dialog-title"
    aria-describedby="alert-dialog-description"
  >
    <DialogTitle id="alert-dialog-title">Message</DialogTitle>
    <DialogContent>
      <DialogContentText id="alert-dialog-description">
        {props.message}
      </DialogContentText>
    </DialogContent>
    <DialogActions>
      <Button onClick={props.onCancel} color="primary" autoFocus>
        Cancel
      </Button>
      <Button onClick={props.onOk} color="secondary" autoFocus>
        Ok
      </Button>
    </DialogActions>
  </Dialog>
);

export default YesNo;
