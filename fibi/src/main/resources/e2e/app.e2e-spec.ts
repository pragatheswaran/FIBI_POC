import { EasytreePage } from './app.po';

describe('easytree App', function() {
  let page: EasytreePage;

  beforeEach(() => {
    page = new EasytreePage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
