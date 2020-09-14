import React, { Component } from "react";
import currency from "currency.js";
import { connect } from "react-redux";
import { withStyles } from "@material-ui/core/styles";
import FullPageDialog from "../../../controls/dialog/FullPageDialog";
import api from "../../../../api";
import Message from "../../../controls/Message";
import { getCartItemsArraySelector } from "../../../../selectors";
import NormalSaleForm from "./NormalSaleForm";
import CircularLoader from "../../../controls/loader/CircularLoader";
import { isValueExists } from "../../../../utils";
import {
  initTransaction,
  cancelTransaction
} from "../../../../actions/transaction";
import { emptyCart } from "../../../../actions/cart";
import YesNo from "../../../controls/dialog/YesNo";
import Prompt from "../../../controls/dialog/Prompt";

const styles = () => ({
  message: {
    margin: 0,
    paddingBottom: "10px"
  },
  formContainer: {
    padding: "30px",
    margin: "auto",
    width: "500px",
    height: "100%"
  }
});

class NormalSale extends Component {
  state = {
    error: "",
    showMessage: false,
    showConfirmDeleteDialog: false,
    showMessageDialog: false,
    isLoading: false,
    errors: {},
    handleClose:'',
    data: {
      cvc: '',
      expiry: '',
      focus: '',
      name: '',
      number: '',
    }
    
  };

  async componentDidMount() {
    try {
      if (this.props.transaction.id) {
        return;
      }

      this.setState({ isLoading: true });

      const res = await api.transaction.getTransactionId();
      const transId = res.data;
      this.props.initTransaction(transId);

      this.setState({ isLoading: false });
    } catch (error) {
      this.setState({
        error: error.message,
        showMessage: true,
        isLoading: false
      });
    }
  }

  onChange = e => {
    const amountPaid = e.target.value;
    const { netTotal } = this.props.cart.summary;
    const balance = currency(amountPaid).subtract(netTotal);

    if (balance.value > 0) {
      this.setState({
        errors: {},
        data: { amountPaid, balanceToPay: balance.toString() }
      });
    } else {
      this.setState({ errors: {}, data: { amountPaid, balanceToPay: "0.00" } });
    }
  };

  onNormalSaleFormSubmit = async e => {
    e.preventDefault();
    try{
    	const errors = isValueExists(this.state.data);
	    if (Object.keys(errors).length > 0) {
	      this.setState({ errors });
	      return;
	    }
	
	    const { cart, cartArray,transaction } = this.props;
	    const { summary } = cart;
	    const { netTotal } = summary;
	
	    const sale = {};
	    sale.items = [];
	    Object.assign(sale.items, cartArray);
	
	    sale.total = currency(summary.total).value;
	    sale.taxAmount = currency(summary.taxAmount).value;
	    sale.totalDiscount = currency(summary.discountOnItems).add(
	      summary.discountOnTotal
	    ).value;
	    sale.netTotal = currency(netTotal).value;
	    sale.transactionId = transaction.id;
	    sale.ccData = this.state.data;
	    const res = await api.transaction.saveNormalSale(sale);
	
	    console.log(res.data);
	    if (res.status === 200) {
	        this.showMessage("Sale successful.");
	      } else {
	    	  this.setState({
	    	        error: "Transaction failed, try again later.",
	    	        showMessage: true,
	    	        isLoading: false
	    	      });
	      }
    }catch(error) {
        this.setState({
            error: error.message,
            showMessage: true,
            isLoading: false
          });
        }
  };
  
  
  
  handleInputFocus = (e) => {
	  console.log('this is in focus');
	  const name = e.target.name ;
      this.setState(prevState=> ({
    	    data: {                   // object that we want to update
    	        ...prevState.data,    // keep all other key-value pairs
    	        focus: name       // update the value of specific key
    	    }
    	}));
  };
    
 handleInputChange = (e) => {
	 console.log('this is in change');
	 const { name, value } = e.target;
  console.log(" name "+ name + " value "+value);
  this.setState(prevState => ({
	  	data: {...prevState.data,[name]: value },
  		errors: { ...prevState.errors, [name]: "" }}));
 
 };

  onNormalSaleFormCancel = () => {
    this.setState({ showConfirmDeleteDialog: true });
    
  };

  onMessageCloseClick = () => {
    this.setState({ showMessage: false });
  };

  // Clear the transaction. If user clicks confirm yes for canceling transaction.
  onYesNoPopYesClick = () => {
    this.props.cancelTransaction();
    this.props.emptyCart();
    this.state.handleClose();
  };

  onYesNoPopNoClick = () => {
    this.setState({ showConfirmDeleteDialog: false });
  };
  onMessageDialogCloseClick = () => {
	    this.setState({ showMessageDialog: false });
	    this.props.cancelTransaction();
	    this.props.emptyCart();
	    this.state.handleClose();
  };
  showMessage = () => {
	    this.setState({
	    	showMessageDialog: true
	      
	    });
  };

  renderForm = (handleClose) => {
    const { transaction, cart } = this.props;
    const { isLoading, errors, data } = this.state;
    
    this.state.handleClose = handleClose;
    if (isLoading === true) {
      return null;
    }

    return (
      <NormalSaleForm
        cart={cart}
        errors={errors}
        data={data}
        onSubmit={this.onNormalSaleFormSubmit}
        onCancel={this.onNormalSaleFormCancel}
        onChange={this.onChange}
      	handleInputChange = {this.handleInputChange}
      	handleInputFocus = {this.handleInputFocus}
        transactionId={transaction.id}
      />
    );
  };

  render() {
    const { handleClose, open, classes } = this.props;
    const {
      error,
      showMessage,
      isLoading,
      showConfirmDeleteDialog
    } = this.state;

    return (
      <FullPageDialog open={open} handleClose={handleClose} title="Sale">
        <CircularLoader isLoading={isLoading} />

        <YesNo
          open={showConfirmDeleteDialog}
          message="Are you sure wan't to cancel the transaction and clear the cart?"
          onOk={() => this.onYesNoPopYesClick()}
          onCancel={() => this.onYesNoPopNoClick()}
        />
        <Prompt
        message="Sale submitted successfully."
        open={this.state.showMessageDialog}
        handleClose={this.onMessageDialogCloseClick}
        />
        <div className={classes.formContainer}>
          <Message
            className={classes.message}
            title="Got an error"
            message={error}
            show={showMessage}
            isError={true}
            onCloseClick={this.onMessageCloseClick}
          />
          {this.renderForm(handleClose)}
        </div>
      </FullPageDialog>
    );
  }
}

function mapStateToProps(state) {
  return {
    transaction: state.transaction,
    cart: state.cart,
    cartArray: getCartItemsArraySelector(state)
  };
}

const mapDispatchToProps = {
  initTransaction,
  cancelTransaction,
  emptyCart
};

const component = withStyles(styles)(NormalSale);
export default connect(mapStateToProps, mapDispatchToProps)(component);
