## ![img](https://forum.quasar-framework.org/uploads/system/site-logo.svg?v=59dc0claf8b)QUASAR-TIPTAP extension

### 다운로드

##### 필수 Dependencies  다운로드

```
npm install --save tiptap tiptap-extensions
```

##### 부가 Dependencies 다운로드(다이어그램 혹은 수식 기능 사용 시)

```
npm install katex mermaid vue-codemirror
```

##### Quasar-Tiptap 다운로드

```
npm install quasar-tiptap@1.7.0
```

### 환경설정

##### quasar.conf.js

```javascript
extras: [
  'mdi-v5'
],
framework: [
  directives: [
    'ClosePopup'
  ]
]
```

##### Import Component

```javascript
<template>
  <div>
    <quasar-tiptap v-bind="options" @update="onUpdate" />
  </div>
</template>

<script>
import { QuasarTiptap, RecommendedExtensions } from 'quasar-tiptap'
import 'quasar-tiptap/lib/index.css'

export default {
  data () {
    return {
      options: {
        content: 'content',
        editable: true,
        extensions: [
          ...RecommendedExtensions,
          // other extenstions
          // name string, or custom extension
        ],
        toolbar: [
          'add-more',
          'separator',
          'bold',
          'italic',
          'underline',
          // other toolbar buttons
          // name string
        ]
      },
      json: '',
      html: ''
    }
  },
  components: {
    QuasarTiptap,
  },
  methods: {
    onUpdate ({ getJSON, getHTML }) {
      this.json = getJSON()
      this.html = getHTML()
      console.log('html', this.html)
    }
  },
  mounted () {
    // set language dynamically
    this.$o.lang.set('en-us')
  }
}
</script>
```

#### * Github에는 안나와있지만 필수적인 설정

##### quasar.conf.js

```javascript
components: [
       'QTooltip',
        'QIcon',
        'QBtn',
        'QSeparator',
        'QInput',
        'QMenu',
        'QBtnDropdown',
        'QItemSection',
        'QItem',
    	// 필요에 따라 개발자 모드를 켜서 확인 후 추가
]
```



---

**[GitHub]** https://github.com/donotebase/quasar-tiptap

**[부가 설명 페이지]** https://libraries.io/npm/quasar-tiptap