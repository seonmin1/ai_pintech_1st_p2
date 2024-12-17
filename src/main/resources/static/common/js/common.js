var commonLib = commonLib ?? {};

// 메타 태그 정보 조회
// mode - rootUrl : <meta name="rootUrl" .../>
commonLib.getMeta = function(mode) {
    if (!mode) return; // mode 값이 없을 경우 return (처리 안함)

    const el = document.querySelector(`meta[name='${mode}']`);

    return el?.content; // ?. - 옵셔널 체이닝 문법
};

// Ajax 요청 처리 함수 생성
// @params url : 요청 주소, http[s] : 외부 URL - 컨텍스트 경로는 추가 안함
// @params method : 요청 방식 - GET, POST, DELETE, PATCH
// @params callback : 응답 완료 후 후속처리 콜백 함수
// @params data : 요청 데이터(POST, PATCH, PUT 일때만 가능)
// @params headers : 추가 요청 헤더
commonLib.ajaxLoad = function(url, callback, method = 'GET', data, headers) { // method 기본값 GET 설정
    if (!url) return; // url 없을 경우 처리 안함

    const { getMeta } = commonLib;
    const csrfHeader = getMeta("_csrf_header");
    const csrfToken = getMeta("_csrf");
    url = /^http[s]?:/.test(url) ? url : getMeta("rootUrl") + url.replace("/", "");

    headers = headers ?? {}; // headers 없으면 빈 배열 반환
    headers[csrfHeader] = csrfToken; // 토큰에 실어서 보냄
    method = method.toUpperCase(); // 통일성을 위해 method 대문자화

    const options = {
        method,
        headers,
    }

    if (data && ['POST', 'PUT', 'PATCH'].includes(method)) { // body 쪽 데이터 추가 가능
        options.body = data instanceof FormData ? data : JSON.stringify(data); // FormData 형식일 때 그대로, 아니면 JSON 형식으로 변환
    }

    return new Promise((resolve, reject) => {
        fetch(url, options)
            .then(res => {
                if (res.status !== 204)
                    return res.json();
                else {
                    resolve();
                }
            })
            .then(json => {
                if (json?.success) { // 응답 성공 (처리 성공) - 후속 처리 callback
                    if (typeof callback === 'function') { // 콜백 함수가 정의된 경우
                        callback(json.data);
                    }

                    resolve(json);

                    return;
                }

                reject(json); // 처리 실패 시
            })
            .catch(err => {
                console.error(err);

                reject(err); // 응답 실패 시
            });
    }); // Promise
};

window.addEventListener("DOMContentLoaded", function() {
    /* 체크박스 전체 토글 기능 S */
    const checkAlls = document.getElementsByClassName("check-all");
    for (const el of checkAlls) {
        el.addEventListener("click", function() {
            const { targetClass } = this.dataset;
            if (!targetClass) { // 토클할 체크박스의 클래스가 설정되지 않은 경우는 진행 X
                return;
            }

            const chks = document.getElementsByClassName(targetClass);
            for (const chk of chks) {
                chk.checked = this.checked;
            }
        });
    }
    /* 체크박스 전체 토글 기능 E */
});