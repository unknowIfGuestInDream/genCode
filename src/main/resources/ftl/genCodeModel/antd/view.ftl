import React, {useEffect} from 'react';
import 'antd/dist/antd.min.css';
import Modal from 'antd/es/modal/Modal';
import {Button} from 'antd';
import ProDescriptions from '@ant-design/pro-descriptions';

const View${module} = (props: any) => {
  const {isView${module}ModalVisible} = props; // 模态框是否显示
  const {is${module}ShowModal} = props; // 操作模态框显示隐藏的方法
  const {${module?uncap_first}Id} = props;

  const handleCancel = () => {
    is${module}ShowModal(false);
  };

  //初始化
  useEffect(() => {

  }, []);

  return (
    <Modal
      title={'查看${moduleDesc}'}
      width="1000px"
      visible={isView${module}ModalVisible}
      onCancel={handleCancel}
      footer={[<Button key="close" type="primary" onClick={handleCancel}>关闭</Button>]}
    >
      <ProDescriptions
        title="${moduleDesc}详情信息"
        column={2}
        request={async (params) => {
          return await load${module}({${antd_primary}: ${module?uncap_first}Id});
        }}
        columns={[
          ${antd_viewForm}
        ]}
      >
      </ProDescriptions>
    </Modal>
  );
};
export default View${module};
