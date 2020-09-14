import axios from "axios";
import "./axiosClient";
import { URL_PREFIX } from './apiBuilder';

const getTransactionId = () => axios.get(`${URL_PREFIX}/sales/initTransaction`);

const saveNormalSale = sale => axios.post(`${URL_PREFIX}/sales/post`, sale);

const saveCreditSale = sale => axios.post(`${URL_PREFIX}/sales/normal`, sale);

// eslint-disable-next-line
export default { getTransactionId, saveNormalSale, saveCreditSale };
