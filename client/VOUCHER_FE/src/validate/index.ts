import * as yup from "yup";
export const schemaSignUp = yup
  .object()
  .shape({
    // firstName: yup.string(),
    // lastName: yup.string(),
    // fullName: yup.string(),
    // roleName: yup.string(),
    // memberCode: yup.string(),
    // address: yup.string(),
    email: yup.string().email().required(),
    // userName: yup.string().required().min(8),
    phone: yup
      .string()
      .min(10)
      .max(12)
      .matches(/^0[0-9]{9}$/, "Phone number must start with 0")
      .required("Please enter the required field"),
    password: yup
      .string()
      .required()
      .min(8, "Password must have at least 8 character")
      .matches(
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).+$/,
        "Password must contain at least one uppercase letter one lowercase letter and one number"
      ),
    confirmPassword: yup
      .string()
      .required()
      .oneOf([yup.ref("password"), null], "Password must be the same"),
  })
  .required();

export const schemaSignIn = yup
  .object()
  .shape({
    emailSignin: yup.string().email().required(),
    passwordSignin: yup
      .string()
      .required()
      .min(8, "Password must have at least 8 character")
      .matches(
        /^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$/,
        "Password must contain at least one letter and one number"
      ),
  })
  .required();
export const schemaForgotPassword = yup
  .object()
  .shape({
    emailForgot: yup.string().email().required(),
  })
  .required();

export const schemaBuyTicket = yup
  .object()
  .shape({
    amount: yup.number().min(1).max(3).required(),
  })
  .required();
export const schemaSetPassword = yup
  .object()
  .shape({
    mail: yup.string().email().required(),
    newpass: yup
      .string()
      .required()
      .min(8, "New Password must have at least 8 character")
      .matches(
        /^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$/,
        "New Password must contain at least one letter and one number"
      ),
    confirmnewpass: yup
      .string()
      .required()
      .oneOf([yup.ref("newpass"), null], "New Password must be the same"),
  })
  .required();
