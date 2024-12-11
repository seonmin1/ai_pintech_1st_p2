const categoryButtons = document.querySelectorAll(".cate");
const card_collection = document.querySelector(".card_collection");
const data = [...document.querySelectorAll(".card")].map((v) => {
  const newObj = {
    link: v.childNodes[1].href,
    src: v.childNodes[1].childNodes[1].childNodes[1].src,
    title: v.childNodes[1].childNodes[3].childNodes[1].innerText,
    subTitle: v.childNodes[1].childNodes[3].childNodes[3].innerText,
  };
  return newObj;
});

const cardCollectionClear = () => (card_collection.innerHTML = "");

const cardMake = (obj) => `
<div class="card">
    <a href="${obj.link}">
        <div class="album">
            <img class="card_img" src="${obj.src}" alt="">
        </div>
        <div class="card_text">
            <h3 class="title">${obj.title}</h3>
            <div class="sub_title">
                <span>${obj.subTitle}</span>
                <span class="arrow_back">
                <img class="arrow" src="imgs/icon_go.png" alt="">
                </span>
            </div>
        </div>
    </a>
</div>
`;

const filterAction = (target) => {
  cardCollectionClear();
  target == "전체" ? true : target;
  const newData = data.filter((v) => v.subTitle.includes(target));
  newData.forEach((v) =>
    card_collection.insertAdjacentHTML("beforeend", cardMake(v))
  );
};

[...categoryButtons].forEach((v) =>
  v.addEventListener("click", () => filterAction(v.innerHTML))
);