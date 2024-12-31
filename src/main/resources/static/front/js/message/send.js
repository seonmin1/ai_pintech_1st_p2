window.addEventListener("DOMContentLoaded", function() {
    const { loadEditor } = commonLib;

    loadEditor("content", 350)
        .then((editor) => {
            window.editor = editor; // 전역 변수로 등록, then 구간 외부에서도 접근 가능하게 처리
        });
});

// 파일 업로드 완료 후 성공 후속처리
function callbackFileUpload(files) {
    if (!files || files.length === 0) { // 파일이 없을 경우 처리 안함
        return;
    }

    const imageUrls = [];

    const targetEditor = document.getElementById("editor-files");
    const targetAttach = document.getElementById("attach-files");
    const tpl = document.getElementById("tpl-file-item").innerHTML;

    const domParser = new DOMParser();

    for (const { seq, fileUrl, fileName, location } of files) {
        let html = tpl;
        html = html.replace(/\[seq\]/g, seq)
                    .replace(/\[fileName\]/g, fileName)
                    .replace(/\[fileUrl\]/g, fileUrl);

        const dom = domParser.parseFromString(html, "text/html");
        const fileItem = dom.querySelector(".file-item");

        if (location === 'editor') { // 에디터에 추가될 이미지
            imageUrls.push(fileUrl);

            targetEditor.append(fileItem);

        } else { // 다운로드를 위한 첨부 파일
            const el = fileItem.querySelector(".insert-editor");
            el.parentElement.removeChild(el);

            targetAttach.append(fileItem);
        }
    }

    if (imageUrls.length > 0) insertImage(imageUrls);
}

function insertImage(imageUrls) {
    editor.execute('insertImage', { source: imageUrls }); // 이미 정해진 명령어
}