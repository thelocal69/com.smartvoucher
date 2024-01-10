import { Spinner, Button } from "react-bootstrap";

const Loading = (props) => {
  const { fileName } = props;
  return (
    <>
      <Button variant="success" disabled>
        <Spinner
          as="span"
          animation="border"
          size="sm"
          role="status"
          aria-hidden="true"
        />
        {fileName}
      </Button>
    </>
  );
};

export default Loading;
