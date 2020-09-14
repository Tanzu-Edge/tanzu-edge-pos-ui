import { withStyles } from "@material-ui/core/styles";
import { TableCell } from "@material-ui/core";

const CustomTableCell = withStyles(() => ({
  head: {
    backgroundColor: "#f5f5f5",
    color: "black",
    fontSize: 14
  },
  body: {
    fontSize: 14
  }
}))(TableCell);

export default CustomTableCell;
