import React, {useEffect, useState} from 'react';
import 'antd/dist/antd.min.css';
import Modal from "antd/es/modal/Modal";
import {Descriptions} from "antd";

const View${module} = (props: any) => {
  const {isView${module}ModalVisible} = props; // 模态框是否显示
  const {is${module}ShowModal} = props; // 操作模态框显示隐藏的方法
  const {${module?uncap_first}Id} = props;
  const [${module?uncap_first}, set${module}] = useState<DataSourceType>({});

  type DataSourceType = {
    ${antd_dataSourceType}
  }

  const get${module} = async () => {
    const response = await load${module}({${antd_primary}: ${module?uncap_first}Id});
    set${module}(response.data);
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
      <Descriptions title="${moduleDesc}详情信息" bordered column={3}>
        ${antd_viewForm}
      </Descriptions>
    </Modal>
  );
};
export default View${module};
