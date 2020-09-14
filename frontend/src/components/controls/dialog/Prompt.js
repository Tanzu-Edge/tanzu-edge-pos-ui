import React from "react";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogActions from '@material-ui/core/DialogActions'
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogContent from '@material-ui/core/DialogContent'

const Prompt = props => (
  <Dialog
    open={props.open}
    onClose={props.handleClose}
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
      <Button onClick={props.handleClose} color="primary" autoFocus>
        Ok
      </Button>
    </DialogActions>
  </Dialog>
);

export default Prompt;
