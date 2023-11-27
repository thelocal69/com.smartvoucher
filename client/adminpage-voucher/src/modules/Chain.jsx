import React, { useState } from "react";
import "./Chain.scss";
import {
  addChain,
  deleteChain,
  editChain,
  getAllChain,
} from "../queries/chain.queries";
import { getAllMerchant } from "../queries/merchant.queries";
import { toast } from "react-toastify";
const statusHard = [
  {
    label: "Active",
    value: 1,
  },
  {
    label: "Deactive",
    value: 0,
  },
];
export const Chain = () => {
  const [idDelete, setIdDelelet] = useState();
  const [list, setList] = React.useState();
  const [chainCode, setChaincode] = useState("");
  const [name, setName] = useState();
  const [legalName, setLegalName] = useState("");
  const [address, setAddress] = useState();
  const [phone, setPhone] = useState();
  const [email, setEmail] = useState();
  const [desc, setDesc] = useState();
  const [merchantCode, setMerchantCode] = useState();
  const [status, setStatus] = useState();
  const [merchantCodes, setMerchantCodes] = useState();
  const [objEdit, setObjEdit] = useState();
  const [objDelete, setObjDelete] = useState();

  const handelEdit = async () => {
    if (!objEdit) {
      return alert("Please select item");
    }
    try {
      const rs = await editChain(objEdit);
      if (rs) {
        toast.success(rs.message);
        const newspaperSpinning = [
          {
            backgroundColor: "white",
          },
          {
            backgroundColor: "rgb(221 215 209)",
          },
        ];
        const newspaperTiming = {
          duration: 500,
        };
        const ele = document.querySelector(`.wrapper .list #anim`);
        ele.animate(newspaperSpinning, newspaperTiming);
        const newlist = await getAllChain();
        return setList(newlist.data);
      }
    } catch (error) {
      toast.error(error.message);
    }
  };
  const handelAdd = async () => {
    if (!chainCode || !name || !status || !legalName || !merchantCode) {
      alert("Please enter enough value");
      return;
    }
    const obj = {
      chainCode: chainCode,
      name: name,
      status: status,
      legalName: legalName,
      address: address,
      description: desc,
      email: email,
      merchantCode: merchantCode,
      phone: phone,
    };
    try {
      const rs = await addChain(obj);
      if (rs) {
        toast.success(rs.message);
        const newlist = await getAllChain();
        return setList(newlist.data);
      }
    } catch (error) {
      toast.error(error.message);
    }
  };
  const handelDelelet = async () => {
    if (!idDelete) {
      alert("Please enter valid ID");
      return;
    }
    try {
      const rs = await deleteChain(idDelete, objDelete);
      if (rs) {
        toast.success(rs.message);
        const newlist = await getAllChain();
        return setList(newlist.data);
      }
    } catch (error) {
      toast.error(error.message);
    }
  };

  React.useEffect(() => {
    getAllChain()
      .then((rs) => setList(rs.data))
      .catch((err) => toast.error(err.message));
    getAllMerchant()
      .then((rs) => {
        const l = rs?.data.map((item) => item.merchantCode);
        setMerchantCodes(l);
      })
      .catch((err) => toast.error(err.message));
  }, []);
  React.useEffect(() => {
    merchantCodes && setMerchantCode(merchantCodes[0]);
  }, [merchantCodes]);
  console.log(merchantCode);
  return (
    <div className="wrapper">
      <div className="list">
        <h2>Data</h2>
        <div className="item head">
          <div className="a id">ID</div>
          <div className="a">chainCode</div>
          <div className="a">name</div>
          <div className="a">legalName</div>
          <div className="a">email</div>
          <div className="a">address</div>
          <div className="a">phone</div>
          <div className="a">description</div>
          <div className="a">merchantCode</div>
          <div className="a">status</div>
        </div>
        {list && list?.length > 0
          ? list?.map((item, key) => {
              return (
                <div
                  className="item"
                  key={key}
                  id={item?.id === objEdit?.id ? "anim" : ""}
                  onClick={() => {
                    setObjEdit(item);
                    setIdDelelet(item?.id);
                    setObjDelete(item);
                  }}
                >
                  <div className="a id">{item?.id}</div>
                  <div className="a">{item?.chainCode}</div>
                  <div className="a">{item?.name}</div>

                  <div className="a">{item?.legalName}</div>
                  <div className="a">{item?.email}</div>
                  <div className="a">{item?.address}</div>
                  <div className="a">{item?.phone}</div>
                  <div className="a">{item?.description}</div>
                  <div className="a">{item?.merchantCode}</div>
                  <div
                    className={item?.status === 0 ? "a deactive" : "a active"}
                  >
                    {item?.status === 0 ? "Deactive" : "Active"}
                  </div>
                </div>
              );
            })
          : "No data"}
      </div>
      <div className="actions">
        <div className="action">
          <h4>ID : {objEdit?.id}</h4>
          <div className="form">
            <input
              type="text"
              name=""
              id=""
              placeholder="chainCode"
              onChange={(e) => {
                let ob = { ...objEdit };
                ob.chainCode = e.target.value;
                setObjEdit(ob);
              }}
              defaultValue={objEdit?.chainCode}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="name"
              onChange={(e) => {
                let ob = { ...objEdit };
                ob.name = e.target.value;
                setObjEdit(ob);
              }}
              defaultValue={objEdit?.name}
            />
            <div
              onChange={(e) => {
                let obj = { ...objEdit };
                obj.status = e.target.value;
                setObjEdit(obj);
              }}
            >
              {statusHard?.map((item) => {
                return (
                  <label>
                    <input
                      checked={Number(objEdit?.status) === Number(item.value)}
                      type="radio"
                      value={item.value}
                    />
                    {item?.label}
                  </label>
                );
              })}
            </div>
            <input
              type="text"
              name=""
              id=""
              placeholder="legalName"
              onChange={(e) => {
                let ob = { ...objEdit };
                ob.legalName = e.target.value;
                setObjEdit(ob);
              }}
              defaultValue={objEdit?.legalName}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="address"
              onChange={(e) => {
                let ob = { ...objEdit };
                ob.address = e.target.value;
                setObjEdit(ob);
              }}
              defaultValue={objEdit?.address}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="phone"
              onChange={(e) => {
                let ob = { ...objEdit };
                ob.phone = e.target.value;
                setObjEdit(ob);
              }}
              defaultValue={objEdit?.phone}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="email"
              onChange={(e) => {
                let ob = { ...objEdit };
                ob.email = e.target.value;
                setObjEdit(ob);
              }}
              defaultValue={objEdit?.email}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="description"
              onChange={(e) => {
                let ob = { ...objEdit };
                ob.description = e.target.value;
                setObjEdit(ob);
              }}
              defaultValue={objEdit?.description}
            />
            <label htmlFor="">Merchant Codes</label>
            <select
              onChange={(e) => {
                let obj = { ...objEdit };
                obj.merchantCode = e.target.value;
                setObjEdit(obj);
              }}
            >
              {merchantCodes?.map((item) => {
                return (
                  <option
                    value={item}
                    selected={item === objEdit?.merchantCode ? true : false}
                  >
                    {item}
                  </option>
                );
              })}
            </select>
          </div>

          <div className="btn" onClick={() => handelEdit()}>
            Edit
          </div>
        </div>
        <div className="action">
          <div className="form">
            <input
              type="text"
              name=""
              id=""
              placeholder="chainCode"
              onChange={(e) => setChaincode(e.target.value)}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="name"
              onChange={(e) => setName(e.target.value)}
            />

            <input
              type="text"
              name=""
              id=""
              placeholder="legalName"
              onChange={(e) => setLegalName(e.target.value)}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="address"
              onChange={(e) => setAddress(e.target.value)}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="phone"
              onChange={(e) => setPhone(e.target.value)}
            />
            <div onChange={(e) => setStatus(e.target.value)}>
              {statusHard?.map((item) => {
                return (
                  <label>
                    <input
                      checked={Number(status) === Number(item.value)}
                      type="radio"
                      value={item.value}
                    />
                    {item?.label}
                  </label>
                );
              })}
            </div>

            <input
              type="text"
              name=""
              id=""
              placeholder="email"
              onChange={(e) => setEmail(e.target.value)}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="desc"
              onChange={(e) => setDesc(e.target.value)}
            />
            <label htmlFor="">Merchant Codes</label>
            <select onChange={(e) => setMerchantCode(e.target.value)}>
              {merchantCodes?.map((item) => {
                return <option value={item}>{item}</option>;
              })}
            </select>
          </div>
          <div className="btn" onClick={() => handelAdd()}>
            Add
          </div>
        </div>
        <div className="action">
          <div className="form">
            <input
              type="text"
              name=""
              id=""
              placeholder="Enter valid id"
              defaultValue={idDelete}
              onChange={(e) => setIdDelelet(e.target.value)}
            />
          </div>
          <div className="btn" onClick={() => handelDelelet()}>
            Delete
          </div>
        </div>
      </div>
    </div>
  );
};
