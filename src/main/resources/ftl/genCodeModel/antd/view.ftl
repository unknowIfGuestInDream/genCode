import React, {useEffect, useState} from 'react';
import 'antd/dist/antd.min.css';
import Modal from "antd/es/modal/Modal";
import {Descriptions, Form} from "antd";

const View${module} = (props: any) => {
  const {isView${module}ModalVisible} = props; // 模态框是否显示
  const {is${module}ShowModal} = props; // 操作模态框显示隐藏的方法
  const {${module?uncap_first}Id} = props;
  const [form] = Form.useForm();
  const [${module?uncap_first}, set${module}] = useState(DataSourceType)({});

  type DataSourceType = {
    ${antd_dataSourceType}
  }

  const get${module} = async () => {
    const response = await load${module}({${antd_primary}: ${module?uncap_first}Id});
    if (response.data != undefined && response.data != null && response.data.length > 0) {
      set${module}(response.data[0]);
    }
  };

  const handleCancel = () => {
    is${module}ShowModal(false);
  };

  //初始化
  useEffect(() => {
    get${module}();
  }, []);

  return (
    <Modal
      title={"查看${moduleDesc}"}
      width="800px"
      visible={isView${module}ModalVisible}
      footer={[]}
      onCancel={handleCancel}
    >
      <Form
        style={{height: '500px', width: '100%', overflow: 'auto'}}
        labelCol={{span: 8}}
        wrapperCol={{span: 16}}
        form={form}
      >
        <Descriptions title="${moduleDesc}详情信息">
          ${antd_viewForm}
        </Descriptions>
      </Form>
    </Modal>
  );
};
export default View${module};
