INSERT INTO user_ (
	id,
    login_id,
    name,
    password,
    type,
    gender,
    cell_phone,
    is_receive_sms,
    email,
    is_receive_email ,
    address,
    detailed_address ,
    is_office_worker,
    is_active,
    note,
    created_on ,
    modified_on
) VALUES (
	'U1370839971594082',
    'jenchae@naver.com', -- loginId
    'Sample User',
    '1111', -- password
    'A',
    'F',
    '010-1234-5678',
    'Y',
    'jenchae@naver.com',
    'Y',
    'Sample Address',
    'Sample Detailed Address',
    'Y',
    true, -- is_active
    'This is a sample note',
    '2013-06-10 13:52:51',
    '2013-06-10 13:52:51'
);