import React from "react";
import currency from "currency.js";
import { Paper } from "@material-ui/core";
import { withStyles } from "@material-ui/core/styles";
import Form from "../../../controls/Form";
import CustomTextField from "../../../controls/textfields/CustomTextField";
import CustomLabel from "./CustomLabel";
import NumberTextField from "../../../controls/textfields/NumberTextField";
import Cards from 'react-credit-cards';
import 'react-credit-cards/es/styles-compiled.css';

const styles = theme => ({
	textField: {
	    [theme.breakpoints.up("xs")]: {
	      width: 150
	    },
	    [theme.breakpoints.up("sm")]: {
	      width: 250
	    },
	    [theme.breakpoints.up("md")]: {
	      width: 350
	    },
	    marginRight: 10
	  }
});

const NormalSaleForm = props => {
  const {
    errors,
    data,
    transactionId,
    cart,
    classes,
    onChange,
    onSubmit,
    onCancel,
    handleInputChange,
    handleInputFocus
  } = props;
  const { summary } = cart;
  

  const totalDiscount = currency(summary.discountOnItems)
    .add(summary.discountOnTotal)
    .toString();
  
  
  return (
    <Paper>
      <Form
        style={{ marginLeft: "0px", padding: "15px" }}
        id="customer"
        onSubmit={onSubmit}
        onCancel={onCancel}
      >
        <CustomLabel
          labelStyle={{ color: "red" }}
          title="Transaction Id"
          text={transactionId}
          helperText="Please note this id incase of any error while saving."
        />
        <br />

        <CustomLabel title="Total bill amount" text={summary.total} />
        <br />

        <CustomLabel title="Total discount amount" text={totalDiscount} />
        <br />

        <CustomLabel title="Total tax" text={summary.taxAmount} />
        <br />

        <CustomLabel title="Grand Total" text={summary.netTotal} />
        <br />

        <div id="PaymentForm">
        <Cards
          cvc={data.cvc}
          expiry={data.expiry}
          focused={data.focus}
          name={data.name}
          number={data.number}
        />
        <br/>
        <CustomTextField
        error={!!errors.number}
        name="number"
        value={data.number}
        placeholder="Card Number"
        onChange={handleInputChange}
        onFocus={handleInputFocus}
        />
        <CustomTextField
        error={!!errors.name}
        name="name"
        value={data.name}
        placeholder="Name"
        onChange={handleInputChange}
        onFocus={handleInputFocus}
        />
        <CustomTextField
        error={!!errors.expiry}
        name="expiry"
        value={data.expiry}
        placeholder="MM/YY"
        onChange={handleInputChange}
        onFocus={handleInputFocus}
        />
        <CustomTextField
        error={!!errors.cvc}
        name="cvc"
        value={data.cvc}	
        placeholder="CVC"
        onChange={handleInputChange}
        onFocus={handleInputFocus}
        />
        </div>
        
        </Form>
    </Paper>
  );
};

export default withStyles(styles, { withTheme: true })(NormalSaleForm);
